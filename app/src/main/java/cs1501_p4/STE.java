/**
 * Spanning Tree Edge class for CS1501 Project 4
 * @author	Dr. Farnan
 */
package cs1501_p4;

public class STE {
	
	/**
	 * One endpoint of this edge
	 */
	protected int u;

	/**
	 * The other endpoint
	 */
	protected int w;

	/**
	 * Basic constructor
	 */
	public STE(int v1, int v2) {
		u = v1;
		w = v2;
	}

	/**
	 * Equality comparison, treating edges as undirected
	 */
	public boolean equals(STE o) {
		if (u == o.u && w == o.w) {
			return true;
		}
		else if (u == o.w && w == o.u) {
			return true;
		}
		else {
			return false;
		}
	}

	public String toString() {
		return "(" + String.valueOf(u) + ", " + String.valueOf(w) + ")";
	}
}
