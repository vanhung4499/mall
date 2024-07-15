package com.union.mall.pms.util;

import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Bloom filter, excerpt from Google Guava package.
 *
 * @author vanhung4499
 */
public class BloomFilterUtils<T> {
    private final int numHashFunctions;
    private final int bitSize;
    private final Funnel<T> funnel;

    public BloomFilterUtils(Funnel<T> funnel, int expectedInsertions, double fpp) {
        checkArgument(funnel != null, "Funnel cannot be null");
        checkArgument(
                expectedInsertions >= 0, "Expected insertions (%s) must be >= 0", expectedInsertions);
        checkArgument(fpp > 0.0, "False positive probability (%s) must be > 0.0", fpp);
        checkArgument(fpp < 1.0, "False positive probability (%s) must be < 1.0", fpp);
        this.funnel = funnel;
        // Calculate bit array length
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        // Calculate number of hash functions
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHash(T value) {
        int[] offsets = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            offsets[i - 1] = combinedHash % bitSize;
        }

        return offsets;
    }

    /**
     * Calculate optimal number of bits for the bit array.
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE; // Set minimum expected length
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * Calculate optimal number of hash functions.
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
