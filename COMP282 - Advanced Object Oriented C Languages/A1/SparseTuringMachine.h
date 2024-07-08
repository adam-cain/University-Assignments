#ifndef SPARSETURINGMACHINE_H_
#define SPARSETURINGMACHINE_H_
#include "TuringMachine.h"
#include <vector>
class SparseTuringMachine : public TuringMachine {
public:
	TuringMachineState* find(int x,int y);
	void add(TuringMachineState& s);
	std::vector<TuringMachineState>* getAll();
private:
	std::vector<TuringMachineState> states_;
};

#endif /* SPARSETURINGMACHINE_H_ */
