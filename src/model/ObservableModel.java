// Keith Williams
// 08/11/2015
// ObservableModel

package model;

import java.util.Observable;

// This class was made abstract so no object of it could be instantiated
public abstract class ObservableModel extends Observable {
	// Methods **********************************
	// Update observers
	public void updateObserver() {
		setChanged();
		notifyObservers();
	}
}
