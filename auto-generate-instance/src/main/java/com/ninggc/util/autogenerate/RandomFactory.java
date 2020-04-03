package com.ninggc.util.autogenerate;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * generate random value
 */
public class RandomFactory {
    Random random = new Random();

    /**
     * get a random string in a specific length
     * @param length
     * @return
     */
    public String getString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int nextInt = getInt(1, 27) + (int) 'A';
            stringBuilder.append((char) nextInt);
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param low inclusive
     * @param high exclusive
     * @return a int value between low and high
     */
    public int getInt(int low, int high) {
        return random.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        RandomFactory randomFactory = new RandomFactory();
        String string = randomFactory.getString(20);
        System.out.println(string);


        String join = Joiner.on(";").skipNulls().join("a", "b", "c");
        System.out.println(join);
        Iterable<String> split = Splitter.on(";").trimResults().omitEmptyStrings().split(join);
        Lists.newArrayList(split);
        System.out.println(split);
        Multiset<Character> multiset = HashMultiset.create();
        for (Character character : "123324765267345236".toCharArray()) {
            multiset.add(character);
        }

        List<Integer> transform = Lists.transform(Lists.newArrayList(multiset.elementSet()), new Function<Character, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable Character input) {
                return Integer.valueOf(input);
            }
        });

        System.out.println("end");
    }
}
