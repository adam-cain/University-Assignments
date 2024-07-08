// TuringMachineState.h

#ifndef TURINGMACHINESTATE_H
#define TURINGMACHINESTATE_H

#include <iostream>
#include <string>

class TuringMachineState {
public:
    TuringMachineState(int currentState, int currentContent, int nextState, int nextContent, const std::string& moveDirection);

    void setCurrentState(int currentState);
    void setCurrentContent(int currentContent);
    void setNextState(int nextState);
    void setNextContent(int nextContent);

    int getCurrentState();
    int getCurrentContent();
    int getNextState();
    int getNextContent();
    std::string getMoveDirection();

    friend std::ostream& operator<<(std::ostream& os, const TuringMachineState& state);
    friend std::istream& operator>>(std::istream& is, TuringMachineState& state);

    bool operator<(const TuringMachineState& other) const;
    bool operator>(const TuringMachineState& other) const;
    bool operator==(const TuringMachineState& other) const;

private:
    int currentState_;
    int currentContent_;
    int nextState_;
    int nextContent_;
    std::string moveDirection_;
};

#endif // TURINGMACHINESTATE_H

