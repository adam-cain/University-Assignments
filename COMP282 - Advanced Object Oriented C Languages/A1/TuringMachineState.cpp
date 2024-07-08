#include "TuringMachineState.h"

TuringMachineState::TuringMachineState(int currentState, int currentContent, int nextState, int nextContent, const std::string &moveDirection)
    : currentState_(currentState), currentContent_(currentContent), nextState_(nextState), nextContent_(nextContent), moveDirection_(moveDirection) {}

void TuringMachineState::setCurrentState(int currentState)
{
    currentState_ = currentState;
}

void TuringMachineState::setCurrentContent(int currentContent)
{
    currentContent_ = currentContent;
}

void TuringMachineState::setNextState(int nextState)
{
    nextState_ = nextState;
}

void TuringMachineState::setNextContent(int nextContent)
{
    nextContent_ = nextContent;
}

int TuringMachineState::getCurrentState()
{
    return currentState_;
}

int TuringMachineState::getCurrentContent()
{
    return currentContent_;
}

int TuringMachineState::getNextState()
{
    return nextState_;
}

int TuringMachineState::getNextContent()
{
    return nextContent_;
}

std::string TuringMachineState::getMoveDirection()
{
    return moveDirection_;
}

std::ostream &operator<<(std::ostream &os, const TuringMachineState &state)
{
    os << state.currentState_ << " " << state.currentContent_ << " " << state.nextState_ << " " << state.nextContent_ << " " << state.moveDirection_;
    return os;
}

std::istream &operator>>(std::istream &is, TuringMachineState &state)
{
    is >> state.currentState_ >> state.currentContent_ >> state.nextState_ >> state.nextContent_ >> state.moveDirection_;
    return is;
}

bool TuringMachineState::operator<(const TuringMachineState &other) const
{
    if (currentState_ != other.currentState_)
    {
        return currentState_ < other.currentState_;
    }
    else
    {
        return currentContent_ < other.currentContent_;
    }
}

bool TuringMachineState::operator>(const TuringMachineState &other) const
{
    if (currentState_ != other.currentState_)
    {
        return currentState_ > other.currentState_;
    }
    else
    {
        return currentContent_ > other.currentContent_;
    }
}

bool TuringMachineState::operator==(const TuringMachineState &other) const
{
    return (currentState_ == other.currentState_) && (currentContent_ == other.currentContent_);
}
