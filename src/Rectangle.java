
public class Rectangle {
	
	public int p1x, p1y;
	public int p2x, p2y;
	public int width;
	public int height;
	
	public Rectangle(int p1x, int p1y,int p2x, int p2y ) {
		
		this.p1x = p1x;
		this.p1y = p1y;
		this.p2x = p2x;
		this.p2y = p2y;
		
		width = p2x-p1x;
		height = p2y-p1y;
	}
	
	

}
