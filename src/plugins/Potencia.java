package plugins;

import main.PluginFunction;

public class Potencia implements PluginFunction {

	private int x,y;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Potencia";
	}

	@Override
	public void setParameter(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		double x1 = x;
		double y1 = y;
		double result = Math.pow(x1, y1);
		return (int) result;
	}

	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}
}
