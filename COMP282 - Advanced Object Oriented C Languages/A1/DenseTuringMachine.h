#ifndef DENSETURINGMACHINE_H_
#define DENSETURINGMACHINE_H_
#include "TuringMachine.h"
#include <vector>
class DenseTuringMachine : public TuringMachine {
public:
	DenseTuringMachine(int x, int y);
	DenseTuringMachine(std::vector<TuringMachineState>* states, int maxState, int maxContent);
	TuringMachineState* find(int x,int y);
	void add(TuringMachineState& s);
	std::vector<TuringMachineState>* getAll();
	void densify();
private:
	std::vector<TuringMachineState> states_;
    int maxCurrentState_;
    int maxCurrentContent_;
	bool isWithinBounds(int x, int y);
};

#endif /* DENSETURINGMACHINE_H_ */