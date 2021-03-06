/*
 * Sux4J: Succinct data structures for Java
 *
 * Copyright (C) 2010-2020 Sebastiano Vigna
 *
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses/>.
 *
 */

package it.unimi.dsi.sux4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.Test;

import it.unimi.dsi.bits.BitVector;
import it.unimi.dsi.bits.BitVectors;
import it.unimi.dsi.bits.HuTuckerTransformationStrategy;
import it.unimi.dsi.bits.LongArrayBitVector;
import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import it.unimi.dsi.util.XoRoShiRo128PlusRandomGenerator;

public class ZFastTrieTest {


	public static String binary(final int l) {
		final String s = "0000000000000000000000000000000000000000000000000000000000000000000000000" + Integer.toBinaryString(l);
		return s.substring(s.length() - 32);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEmpty() throws IOException, ClassNotFoundException {
		final String[] s = {};
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		assertFalse(zft.contains(""));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		assertFalse(zft.contains(""));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSingleton() throws IOException, ClassNotFoundException {
		final String[] s = { "a" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		zft.remove("a");
		assertFalse(zft.contains("a"));

		final ObjectBidirectionalIterator<String> iterator = zft.iterator();
		assertFalse(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDoubleton() throws IOException, ClassNotFoundException {
		final String[] s = { "a", "c" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDoubleton2() throws IOException, ClassNotFoundException {
		final String[] s = { "c", "a" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testTriple() throws IOException, ClassNotFoundException {
		final String[] s = { "a", "b", "c" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testTriple2() throws IOException, ClassNotFoundException {
		final String[] s = { "c", "b", "a" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExitNodeIsLeaf() throws IOException, ClassNotFoundException {
		final String[] s = { "a", "aa", "aaa" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExitNodeIsLeaf3() throws IOException, ClassNotFoundException {
		final String[] s = { "a", "aa", "aaa" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testSmallest() throws IOException, ClassNotFoundException {
		final String[] s = { "a", "b", "c", "d", "e", "f", "g" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSmallest2() throws IOException, ClassNotFoundException {
		final String[] s = { "g", "f", "e", "d", "c", "b", "a" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSmall() throws IOException, ClassNotFoundException {
		final String[] s = { "-", "0", "1", "4", "5", "a", "b", "c", "d", "e", "f", "g", "}" };
		ZFastTrie<String> zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());
		for (int i = s.length; i-- != 0;)
			assertTrue(s[i], zft.contains(s[i]));
		final File temp = File.createTempFile(getClass().getSimpleName(), "test");
		temp.deleteOnExit();
		BinIO.storeObject(zft, temp);
		zft = (ZFastTrie<String>)BinIO.loadObject(temp);
		for (int i = s.length; i-- != 0;)
			assertTrue(zft.contains(s[i]));

		for (int i = s.length; i-- != 0;) {
			assertTrue(zft.remove(s[i]));
			assertFalse(zft.contains(s[i]));
		}
	}


	@Test
	public void testEmptyLcp() {
		final ZFastTrie<BitVector> zft = new ZFastTrie<>(TransformationStrategies.identity());
		assertTrue(zft.add(LongArrayBitVector.of(0, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.add(BitVectors.ONE));
		assertTrue(zft.contains(LongArrayBitVector.of(0, 0)));
		assertTrue(zft.contains(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.contains(BitVectors.ONE));
		assertTrue(zft.remove(BitVectors.ONE));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 0)));

		assertTrue(zft.add(LongArrayBitVector.of(0, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.add(BitVectors.ONE));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.remove(BitVectors.ONE));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 0)));

		assertTrue(zft.add(LongArrayBitVector.of(0, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.add(BitVectors.ONE));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 1)));
		assertTrue(zft.remove(LongArrayBitVector.of(0, 0)));
		assertTrue(zft.remove(BitVectors.ONE));

		assertTrue(zft.add(LongArrayBitVector.of(1, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.add(BitVectors.ZERO));
		assertTrue(zft.contains(LongArrayBitVector.of(1, 0)));
		assertTrue(zft.contains(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.contains(BitVectors.ZERO));
		assertTrue(zft.remove(BitVectors.ZERO));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 0)));

		assertTrue(zft.add(LongArrayBitVector.of(1, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.add(BitVectors.ZERO));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.remove(BitVectors.ZERO));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 0)));

		assertTrue(zft.add(LongArrayBitVector.of(1, 0)));
		assertTrue(zft.add(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.add(BitVectors.ZERO));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 1)));
		assertTrue(zft.remove(LongArrayBitVector.of(1, 0)));
		assertTrue(zft.remove(BitVectors.ZERO));
	}

	@Test
	public void testManyBranches() {
		final ZFastTrie<BitVector> zft = new ZFastTrie<>(TransformationStrategies.identity());
		for (int p = 0; p < 10; p++) {
			for (int i = 0; i < (1 << p); i++)
				assertTrue(zft.add(LongArrayBitVector.getInstance().append(i, p)));
			for (int i = 0; i < (1 << p); i++)
				assertTrue(zft.contains(LongArrayBitVector.getInstance().append(i, p)));
			for (int i = 0; i < (1 << p); i++)
				assertTrue(zft.remove(LongArrayBitVector.getInstance().append(i, p)));
			for (int i = 0; i < (1 << p); i++)
				assertTrue(zft.add(LongArrayBitVector.getInstance().append(i, p)));
			for (int i = (1 << p); i-- != 0;)
				assertTrue(zft.remove(LongArrayBitVector.getInstance().append(i, p)));
		}
	}

	@Test
	public void testLinear() {
		final ZFastTrie<BitVector> zft = new ZFastTrie<>(TransformationStrategies.identity());
		for (int p = 0; p < 20; p++)
			assertTrue(zft.add(LongArrayBitVector.getInstance().append(1 << p, p + 1)));
		for (int p = 0; p < 20; p++)
			assertTrue(zft.contains(LongArrayBitVector.getInstance().append(1 << p, p + 1)));
		for (int p = 0; p < 20; p++)
			assertTrue(zft.remove(LongArrayBitVector.getInstance().append(1 << p, p + 1)));
		for (int p = 0; p < 20; p++)
			assertTrue(zft.add(LongArrayBitVector.getInstance().append(1 << p, p + 1)));
		for (int p = 20; p-- != 0;)
			assertTrue(zft.remove(LongArrayBitVector.getInstance().append(1 << p, p + 1)));
	}

	@Test
	public void testExtent() {
		final ZFastTrie<LongArrayBitVector> zft = new ZFastTrie<>(TransformationStrategies.identity());
		final LongArrayBitVector v = LongArrayBitVector.getInstance();
		v.add(0);
		v.add(1);
		zft.add(v);
		final LongArrayBitVector w = LongArrayBitVector.getInstance();
		w.add(0);
		w.add(0);
		zft.add(w);
		final LongArrayBitVector q = LongArrayBitVector.getInstance();
		q.add(0);
		zft.contains(q);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testNumbers() throws IOException, ClassNotFoundException {
		ZFastTrie<String> zft;
		File temp;
		final RandomGenerator random = new XoRoShiRo128PlusRandomGenerator(1);

		for (int d = 10; d < 10000; d *= 10) {
			final String[] s = new String[d];

			for (int rand = 0; rand < 2; rand++) {
				for (int i = s.length; i-- != 0;)
					s[i] = binary(i);

				for (int pass = 0; pass < 2; pass++) {

					zft = new ZFastTrie<>(Arrays.asList(s), TransformationStrategies.prefixFreeIso());

					for (int i = s.length; i-- != 0;)
						assertTrue(s[i], zft.contains(s[i]));

					// Exercise code for negative results
					for (int i = 1000; i-- != 0;)
						zft.contains(binary(i * i + d));

					temp = File.createTempFile(getClass().getSimpleName(), "test");
					temp.deleteOnExit();
					BinIO.storeObject(zft, temp);
					zft = (ZFastTrie<String>)BinIO.loadObject(temp);
					for (int i = s.length; i-- != 0;)
						assertTrue(s[i], zft.contains(s[i]));

					zft = new ZFastTrie<>(Arrays.asList(s), new HuTuckerTransformationStrategy(Arrays.asList(s), true));

					for (int i = s.length; i-- != 0;)
						assertTrue(s[i], zft.contains(s[i]));

					temp = File.createTempFile(getClass().getSimpleName(), "test");
					temp.deleteOnExit();
					BinIO.storeObject(zft, temp);
					zft = (ZFastTrie<String>)BinIO.loadObject(temp);
					for (int i = s.length; i-- != 0;)
						assertTrue(s[i], zft.contains(s[i]));

					Collections.sort(Arrays.asList(s));

					int p = 0;
					ObjectBidirectionalIterator<String> iterator;
					for (iterator = zft.iterator(); iterator.hasNext();)
						assertEquals(iterator.next(), s[p++]);
					while (iterator.hasPrevious())
						assertEquals(iterator.previous(), s[--p]);

					for (int i = 0; i < s.length / 100; i++) {
						p = i;
						for (iterator = zft.iterator(s[i]); iterator.hasNext();)
							assertEquals(iterator.next(), s[p++]);
						while (iterator.hasPrevious())
							assertEquals(iterator.previous(), s[--p]);
					}

					for (int i = s.length; i-- != 0;) {
						assertTrue(zft.remove(s[i]));
						assertFalse(zft.contains(s[i]));
					}

					Collections.shuffle(Arrays.asList(s), new XoRoShiRo128PlusRandom(1));
				}
			}

			for (int i = s.length; i-- != 0;)
				s[i] = binary(random.nextInt(Integer.MAX_VALUE));

		}
	}
}
