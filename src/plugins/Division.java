package plugins;

import java.text.DecimalFormat;

import main.PluginFunction;

public class Division implements PluginFunction {

	private int x,y;
	private boolean error = false;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Division";
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
		if (y == 0) {
			error = true;
			return "Division por cero";
		}
		float res = ((float)x)/y;
		DecimalFormat df = new DecimalFormat("#.##");
		String r = df.format(res);
		System.out.println("res div "+r);
		return r;
	}

	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return error;
	}

}
