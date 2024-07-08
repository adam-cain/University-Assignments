#ifndef TURINGTAPE_H_
#define TURINGTAPE_H_

#include <iostream>
#include <vector>

class TuringTape {
public:
	TuringTape(int n);
	bool moveRight();
	bool moveLeft();
	int getContent();
	void setContent(int c);
	int getPosition();

	friend std::ostream& operator<<(std::ostream& out,const TuringTape& s);

private:
    std::vector<int> tape;
    int n; 
    int position; 
	int highestPosition;
};

#endif /* TURINGTAPE_H_ */
