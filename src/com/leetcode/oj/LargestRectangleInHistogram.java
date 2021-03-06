package com.leetcode.oj;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by doliu on 10/6/14.
 */
public class LargestRectangleInHistogram {
	public int largestRectangleArea(int[] height) {
		if (height == null || height.length == 0) return 0;
		int max = 0, n = height.length;
		Deque<Integer> stack = new ArrayDeque<>();
		stack.push(0);
		for (int i = 1; i < n; i++) {
			int top = stack.peek();
			while (!stack.isEmpty() && height[top] > height[i]) {
				int popped = stack.pop();
				top = stack.isEmpty() ? -1 : stack.peek();
				int area = (i - top - 1) * height[popped];
				max = Math.max(max, area);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int popped = stack.pop();
			int top = stack.isEmpty() ? -1 : stack.peek();
			int area = (n - top - 1) * height[popped];
			max = Math.max(max, area);
		}
		return max;
	}

	public int largestRectangleAreaOnePass(int[] height) {
		if (height == null || height.length == 0) return 0;
		int[] h = new int[height.length + 1];
		System.arraycopy(height, 0, h, 0, height.length);
		h[height.length] = 0;
		int max = 0;
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = 0; i < h.length; i++) {
			if (stack.isEmpty() || h[stack.peek()] <= h[i])
				stack.push(i);
			else {
				int cur = stack.pop();
				max = Math.max(max, h[cur] * (stack.isEmpty() ? i : (i - stack.peek() - 1)));
				i--; // keep end index at current position while popping until stack top is less than current value
			}
		}
		return max;
	}

	public int largestRectangleAreaVBrute(int[] height) {
		if (height == null || height.length == 0) return 0;
		int max = 0, n = height.length;
		for (int i = 0; i < n; i++) {
			int maxArea = height[i], minHeight = height[i];
			for (int j = i + 1; j < n; j++) {
				minHeight = Math.min(minHeight, height[j]);
				maxArea = Math.max(maxArea, (j - i + 1) * minHeight);
			}
			max = Math.max(max, maxArea);
		}
		return max;
	}
}
