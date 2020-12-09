package plugins;

import main.PluginFunction;

public class Suma implements PluginFunction {

	private int x,y;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Suma";
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
		return x+y;
	}

	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}
}
