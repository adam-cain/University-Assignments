#include "TuringTape.h"
#include <limits>

TuringTape::TuringTape(int n)
{
    if (n == -1){
        this->n = std::numeric_limits<int>::max();
    } else {
        this->n = n;
    }
    tape.push_back(0);
    position = 0;
    highestPosition = 0;
}

bool TuringTape::moveRight()
{
    position++;
    if(position < n && position > highestPosition){
        highestPosition = position;
        tape.push_back(0);
    }
    return position < n && position >= 0;
}

bool TuringTape::moveLeft()
{
    position--;
    return position < n && position >= 0;
}

int TuringTape::getContent()
{
    if (position >= 0 && position < tape.size()){
        return tape[position];
    }
    return 0;
}

void TuringTape::setContent(int c)
{
    if (position >= 0 && position < n){
        tape[position] = c;
    }
}

int TuringTape::getPosition()
{
    return position;
}

std::ostream &operator<<(std::ostream &out, const TuringTape &T)
{
    for (int i = 0; i <= T.highestPosition; i++){
        out << T.tape[i]; 
    }
    return out;
}