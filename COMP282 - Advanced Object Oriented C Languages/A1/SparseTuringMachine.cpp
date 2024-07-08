#include "SparseTuringMachine.h"

TuringMachineState *SparseTuringMachine::find(int state, int content)
{
    for (TuringMachineState &tm : states_)
    {
        if (tm.getCurrentState() == state && tm.getCurrentContent() == content)
        {
            return &tm;
        }
    }
    return nullptr; 
}

void SparseTuringMachine::add(TuringMachineState &s)
{
    states_.push_back(s);
}

std::vector<TuringMachineState> *SparseTuringMachine::getAll()
{
    return &states_;
}
