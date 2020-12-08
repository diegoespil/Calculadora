package main;




public interface PluginFunction {

	public String toString();
	//which returns the name of the plugin, so that it can be displayed to the user in some way

	public void setParameter (int x, int y);
	//which lets the hosting application set a parameter for the plugin to work with

	public Object getResult();
	//through which the application can retrieve the result of the plugins work, i.e. the actual workhorse call

	public boolean hasError();
	//which signals that the previous call to the plugin was not successful
}
