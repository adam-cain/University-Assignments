#include <iostream>
#include "TuringMachineState.h"
#include <vector>
#include "DenseTuringMachine.h"
#include "TuringTape.h"
#include "SparseTuringMachine.h"
#include "MenuSystem.h"
#include <algorithm>

using namespace std;

// 1
/**
 * The function creates two instances of the TuringMachineState class and prints their current state,
 * current content, next state, next content, and move direction.
 */
void checkFirst()
{
	TuringMachineState t1(1, 2, 3, 4, "->");
	TuringMachineState t2(5, 6, 7, 8, "<-");
	cout << t1.getCurrentState() << " " << t1.getCurrentContent() << " " << t1.getNextState() << " " << t1.getNextContent() << " " << t1.getMoveDirection() << endl;
	cout << t2.getCurrentState() << " " << t2.getCurrentContent() << " " << t2.getNextState() << " " << t2.getNextContent() << " " << t2.getMoveDirection() << endl;
}

// 2
/**
 * The function creates two instances of the TuringMachineState class, reads input for their
 * properties, and prints out their values.
 */
void checkSecond()
{
	TuringMachineState t1(1, 2, 3, 4, "->");
	cout << t1 << endl;
	TuringMachineState t2(5, 6, 7, 8, "<-");
	cout << t2 << endl;
	cin >> t1;
	cin >> t2;
	cout << t1.getCurrentState() << " " << t1.getCurrentContent() << " " << t1.getNextState() << " " << t1.getNextContent() << " " << t1.getMoveDirection() << endl;
	cout << t2.getCurrentState() << " " << t2.getCurrentContent() << " " << t2.getNextState() << " " << t2.getNextContent() << " " << t2.getMoveDirection() << endl;
}

// 3
/**
 * The function tests all possible combinations of TuringMachineState objects in a vector and outputs
 * the results of their comparison operations.
 *
 * @param vec The parameter "vec" is a vector of TuringMachineState objects.
 */
void testAll(vector<TuringMachineState> vec)
{
	for (auto s : vec)
	{
		for (auto t : vec)
		{
			cout << (s < t) << (s > t) << (s == t) << endl;
		}
	}
}

/**
 * The function creates a vector of TuringMachineState objects and tests them using the testAll
 * function.
 */
void checkThird()
{
	vector<TuringMachineState> vec;
	vec.push_back(TuringMachineState(1, 2, 3, 4, "->"));
	vec.push_back(TuringMachineState(2, 1, 7, 8, "<-"));
	vec.push_back(TuringMachineState(2, 1, 3, 4, "->"));
	vec.push_back(TuringMachineState(1, 1, 10, 10, "<-"));
	testAll(vec);
}

// 4
/**
 * The function compares two Turing machine states based on their current state and content.
 *
 * @param s1 The first Turing machine state to be compared.
 * @param s2 The second Turing machine state that is being compared to the first state (s1) using the
 * compareState function.
 *
 * @return a boolean value, which is the result of the comparison between two TuringMachineState
 * objects.
 */
bool compareState(TuringMachineState s1, TuringMachineState s2)
{
	return (s1.getCurrentState() < s2.getCurrentState()) || (s1.getCurrentState() == s2.getCurrentState()) && s1.getCurrentContent() < s2.getCurrentContent();
}

// 4, 7 & 8
/**
 * The function checks the functionality of a Turing machine by adding states, finding states, and
 * printing all states.
 *
 * @param t The parameter "t" is a pointer to a TuringMachine object.
 */
void checkTuringMachine(TuringMachine *t)
{
	TuringMachineState s1(1, 2, 3, 4, "->");
	t->add(s1);
	TuringMachineState s2(5, 6, 7, 8, "<-");

	t->add(s2);
	cout << *t->find(1, 2);
	cout << *t->find(5, 6) << endl;
	cout << (t->find(1, 3) == NULL) << endl;
	vector<TuringMachineState> vec = *t->getAll();
	sort(vec.begin(), vec.end(), compareState);
	for (auto s : *t->getAll())
	{
		cout << s;
	}
}

void checkFourth()
{
	DenseTuringMachine d(10, 10);
	checkTuringMachine(&d);
}

void checkTape(TuringTape t)
{
	cout << t.moveLeft();
	for (int i = 0; i < 10; i++)
	{
		cout << t.moveRight();
		t.setContent(i);
	}
	for (int i = 0; i < 10; i++)
	{
		cout << t.moveLeft();
		cout << t.getContent();
	}
}

void checkFifth()
{
	TuringTape t(10);
	checkTape(t);
}

void checkMenu()
{
	MenuSystem m;
	m.menu();
}

void checkSeventh()
{
	SparseTuringMachine s;
	checkTuringMachine(&s);
	checkMenu();
}

void checkEigth()
{
	TuringTape t(-1);
	checkTape(t);

	DenseTuringMachine d(-1, -1);
	checkTuringMachine(&d);

	checkMenu();
}

int main()
{
	int task;
	cin >> task;
	if (task == 1)
		checkFirst();
	if (task == 2)
		checkSecond();
	if (task == 3)
		checkThird();
	if (task == 4)
		checkFourth();
	if (task == 5)
		checkFifth();
	if (task == 6)
		checkMenu();
	if (task == 7)
		checkSeventh();
	if (task == 8)
		checkEigth();
	cin >> task;
}
