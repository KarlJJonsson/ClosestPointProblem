import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPointProblem {
	static Point point1 = null;
	static Point point2 = null;
	static double minDist;
	
	public static void main(String[] args) {
		ArrayList<Point> list = new ArrayList<Point>();
		Point p1 = new Point(2,3);
		Point p2 = new Point(12,30);
		Point p3 = new Point(40,50);
		Point p4 = new Point(5,1);
		Point p5 = new Point(12,10);
		Point p6 = new Point(3,4);
		list.add(p1);
		list.add(p2); 
		list.add(p3); 
		list.add(p4); 
		list.add(p5); 
		list.add(p6);
		//add values to list here.
		
		findMinDistance(list);
		System.out.print("Closest points are Point: " + point1.getX() + "," + point1.getY() + " and Point: " + point2.getX() + "," + point2.getY() 
				+ " with a distance of: " + minDist);
	}
	
	public static void findMinDistance(ArrayList<Point> list) {
		if (list.size() <= 1) {
			System.out.println("List has to contain more than one Point");
			return;
		}
		if (list.size()<3) {
			minDist = distance(list.get(0), list.get(1));
			point1 = list.get(0);
			point2 = list.get(1);
			return;
		}
		Point[] sortedByX = preProcessX(list);
		Point[] sortedByY = preProcessY(list);
		
		minDist = findClosestPoint(sortedByX, sortedByY, 0, list.size()-1);
	}
	/**
	 * Finds the 2 closest points given a sequence of Point objects. Using the divide and conquer technique to solve the problem
	 * in O(nlogn) time. 
	 * 
	 * @param xSorted	an array of Points sorted by their value X in ascending order.
	 * @param ySorted	an array of Points sorted by their value Y in ascending order.
	 * @param bot		the first index of given sequence.
	 * @param top		the last index of given sequence.
	 * @return			returns a double of distance d between the two closest points in the sequence.
	 */
	public static double findClosestPoint(Point[] xSorted, Point[] ySorted, int bot, int top) {
		if(top <= bot) {
			return Double.POSITIVE_INFINITY;
		}
		
		int mid = (bot + top) / 2;
		Point median = xSorted[mid];
		
		double d1 = findClosestPoint(xSorted, ySorted, bot, mid);
		double d2 = findClosestPoint(xSorted, ySorted, mid+1, top);
		double d = Math.min(d1,d2);
		
		if (top - bot == 1 && d == Double.POSITIVE_INFINITY) {
			d = distance(xSorted[bot], xSorted[top]);
			point1 = xSorted[bot];
			point2 = xSorted[top];
		}
		
		ArrayList<Point> strip = new ArrayList<Point>();
		
		for(int i = 0; i < ySorted.length; i++) {
			if (Math.abs(ySorted[i].getX() - median.getX()) < d) {
				strip.add(ySorted[i]);
			}
		}
		
		for(int i = 0; i < strip.size(); i++) {
			for(int j = i + 1; j < strip.size()-1; j++) {
				if(Math.abs(strip.get(i).getY() - strip.get(i).getY()) > d) {
					break;
				}
				else if(distance(strip.get(i), strip.get(j)) < d){
					d = distance(strip.get(i), strip.get(j));
					point1 = strip.get(i);
					point2 = strip.get(j);
				}
			}
		}
		
		return d;
	}
	
	public static double distance(Point p1, Point p2) {
		int x = p1.getX() - p2.getX();
		int y = p1.getY() - p2.getY();
		return Math.sqrt(x * x + y * y);
	}
	
	public static Point[] preProcessX(ArrayList<Point> list) {
		Point[] sortedByX = new Point[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			sortedByX[i] = list.get(i);
		}
		
		Arrays.sort(sortedByX, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.getX() - p2.getX();
			}
		});
		 return sortedByX;
	}
	
	public static Point[] preProcessY(ArrayList<Point> list) {
		Point[] sortedByY = new Point[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			sortedByY[i] = list.get(i);
		}
		
		Arrays.sort(sortedByY, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.getY() - p2.getY();
			}
		});
		 return sortedByY;
	}
}
