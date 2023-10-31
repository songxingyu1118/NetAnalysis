/**
 * Basic tests for CS1501 Project 3
 * @author	Dr. Farnan
 */
package cs1501_p4;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;

import static java.time.Duration.ofSeconds;

class AllTest {
	final int DEFAULT_TIMEOUT = 10;
	NetAnalysis na;

	@BeforeEach
	void init_na() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			na = new NetAnalysis("build/resources/test/network_data1.txt");
		});
	}

	@Test
	@DisplayName("Lowest Latency Path")
	void llp() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			ArrayList<Integer> res = na.lowestLatencyPath(0, 4);

			ArrayList<Integer> exp = new ArrayList<Integer>();
			exp.add(0);
			exp.add(4);

			for (int i = 0; i < exp.size(); i++) {
				System.out.printf("Expected %d, result %d\n", exp.get(i), res.get(i));
				assertEquals(exp.get(i), res.get(i), "Incorrect vertex on path");
			}
		});
	}

	@Test
	@DisplayName("Bandwidth Along Path")
	void bap() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			ArrayList<Integer> path = na.lowestLatencyPath(0, 4);
			int res = na.bandwidthAlongPath(path);

			assertEquals(100, res, "Incorrect bandwidth");
		});
	}

	@Test
	@DisplayName("Copper Only Connected")
	void coc() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			assertTrue(na.copperOnlyConnected());
		});
	}

	@Test
	@DisplayName("Survives Any Two Failed Vertices")
	void stfv() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			assertTrue(na.connectedTwoVertFail());
		});
	}

	@Test
	@DisplayName("Lowest Average Latency Spanning Tree")
	void lalst() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			ArrayList<STE> res = na.lowestAvgLatST();

			ArrayList<STE> exp = new ArrayList<STE>();
			exp.add(new STE(0, 4));
			exp.add(new STE(1, 4));
			exp.add(new STE(2, 4));
			exp.add(new STE(3, 4));

			assertEquals(exp.size(), res.size(), "Incorrect number of spanning tree edges");
			for (STE i : exp) {
				boolean found = false;
				for (STE j : res) {
					if (i.equals(j)) {
						found = true;
						break;
					}
				}
				assertTrue(found, "Invalid spanning tree edge");
			}
		});
	}
}
