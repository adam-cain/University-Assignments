#include "DenseTuringMachine.h"
#include <algorithm>
#include <limits>

DenseTuringMachine::DenseTuringMachine(int maxState, int maxContent) {
    if(maxState == -1){
        maxCurrentState_ = std::numeric_limits<int>::max();
    }else{
        maxCurrentState_ = maxState;
    }

    if(maxContent == -1){
        maxCurrentContent_ = std::numeric_limits<int>::max();
    }else{
        maxCurrentContent_ = maxContent;
    }
}

DenseTuringMachine::DenseTuringMachine(std::vector<TuringMachineState>* states, int maxState, int maxContent) {
    maxCurrentState_ = maxState;
    maxCurrentContent_ = maxContent;
    std::vector<TuringMachineState>* ptr = states;
    states_ = *ptr;
}

TuringMachineState* DenseTuringMachine::find(int state, int content) {
    
    for (TuringMachineState& tm : states_) {
        if (tm.getCurrentState() == state && tm.getCurrentContent() == content) {
            return &tm;
        }
    }
    return nullptr; 
}

bool DenseTuringMachine::isWithinBounds(int state, int content) {
    
    if (state <= maxCurrentState_ && content <= maxCurrentContent_) {
        return true;
    } else {
        return false;
    }
}

void DenseTuringMachine::add(TuringMachineState& s) {
    if(isWithinBounds(s.getCurrentState(), s.getCurrentContent())){
       states_.push_back(s);
    }
}

std::vector<TuringMachineState>* DenseTuringMachine::getAll() {
    
    return &states_;
}

void DenseTuringMachine::densify()
{
    auto cmpStates = [](const TuringMachineState& s1, const TuringMachineState& s2) {
        TuringMachineState& s1_nonconst = const_cast<TuringMachineState&>(s1);
        TuringMachineState& s2_nonconst = const_cast<TuringMachineState&>(s2);
        return s1_nonconst.getCurrentState() < s2_nonconst.getCurrentState();
    };

    std::sort(states_.begin(), states_.end(), cmpStates);

    int statesLen = sizeof(states_) / sizeof(states_[0]);

    for (int i = 0; i < statesLen; i++) {
     states_[i].setCurrentState(i);
    }

    auto cmpContent = [](const TuringMachineState& s1, const TuringMachineState& s2) {
        TuringMachineState& s1_nonconst = const_cast<TuringMachineState&>(s1);
        TuringMachineState& s2_nonconst = const_cast<TuringMachineState&>(s2);
        return s1_nonconst.getCurrentContent() < s2_nonconst.getCurrentContent();
    };
    
    std::sort(states_.begin(), states_.end(), cmpContent);

    for (int i = 0; i < statesLen; i++) {
     states_[i].setCurrentContent(i);
    }

}