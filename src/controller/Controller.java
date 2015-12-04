// Keith Williams
// 30/11/2015
// Controller interface - implemented in all controller classes.
//							Contains two abstract methods which are used to set the model and view variables.

package controller;

public interface Controller {
	void setModel(Object o);
	void setView(Object o);
}
