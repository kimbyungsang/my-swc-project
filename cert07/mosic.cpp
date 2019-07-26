#include <iostream>
using namespace std;

#define MAXCANVAS 1000
#define MAXPAPER 10000
#define MAXSIZE 50
#define MAXCMD 20000

struct Node {
	int id;
	int y;
	int x;
	int yy;
	int xx;
	int color;
	bool flag;

	Node* next = nullptr;
	Node* prev = nullptr;
};

Node _node[30000];
int nodeCnt = 0;
int canvas[1000][1000];
Node* bucket[20][20];

int idIdx[10000][2];
int by, bx;

Node* newNode(int id, int y, int x, int yy, int xx, int color) {
	_node[nodeCnt].id = id;
	_node[nodeCnt].y = y;
	_node[nodeCnt].x = x;
	_node[nodeCnt].yy = yy;
	_node[nodeCnt].xx = xx;
	_node[nodeCnt].color = color;
	_node[nodeCnt].flag = 1;

	return &_node[nodeCnt++];
}

Node* newNode() {
	_node[nodeCnt].id = 10000;
	return &_node[nodeCnt++];
}


void init(int canvasSize) {
	nodeCnt = 0;
	for (int i = 0; i < 1000; i++) {
		for (int j = 0; j < 1000; j++) {
			canvas[i][j] = 0;
		}
	}

	for (int i = 0; i < 20; i++) {
		for (int j = 0; j < 20; j++) {
			bucket[i][j] = newNode();
			bucket[i][j]->next = bucket[i][j];
			bucket[i][j]->prev = bucket[i][j];
		}
	}
}

void create(int id, int y, int x, int height, int width, unsigned char color) {
//	cout << id << " " << y << " " << x << " " << height << " " << width << " " << (int)color << endl;
//	cout << (y / MAXSIZE) <<" " << (x / MAXSIZE) << endl;
	
	by = y / 50;
	bx = x / 50;
	Node* node = newNode(id, y, x, height, width, (int)color);
	
	node->next = bucket[by][bx];
	node->prev = bucket[by][bx]->prev;
	node->next->prev = node;
	node->prev->next = node;

	idIdx[id][0] = by;
	idIdx[id][1] = bx;
}

void remove(int id) {
	Node* node = bucket[idIdx[id][0]][idIdx[id][1]];
	for (Node* iter = node->next; iter != node; iter = iter->next) {
		if (iter->id == id) {
//			iter->flag = 0;
			iter->next->prev = iter->prev;
			iter->prev->next = iter->next;
			break;
		}
	}
}

void move(int id, int y, int x) {
	by = y / 50;
	bx = x / 50;
	int yy;
	int xx;
	int color;

	Node* node = bucket[idIdx[id][0]][idIdx[id][1]];
	Node* node2 = nullptr;
	for (Node* iter = node->next; iter != node; iter = iter->next) {
		if (iter->id == id) {
			iter->next->prev = iter->prev;
			iter->prev->next = iter->next;
			node2 = iter;
			break;
		}
	}

	node2->next = bucket[by][bx];
	node2->prev = bucket[by][bx]->prev;
	node2->next->prev = node2;
	node2->prev->next = node2;


	idIdx[id][0] = by;
	idIdx[id][1] = bx;

//	create(id, y, x, yy, xx, color);
}

void fill(Node* node) {
	for (int i = node->y; i < (node->y + node->yy); i++) {
		for (int j = node->x; j < (node->x + node->xx); j++) {
			canvas[i][j] = node->color;
		}
	}
}

void getPattern(int y, int x, unsigned char pattern[5][5]) {
	by = y / 50;
	bx = x / 50;

	Node* node;

	for (int i = by - 1; i <= by + 1; i++) {
		for (int j = bx - 1; j <= bx + 1; j++) {
			if (i < 0 || i > 19 || j < 0 || j > 19)
				continue;
			for (Node* iter = bucket[i][j]->next; iter != bucket[i][j]; iter = iter->next) {
				fill(iter);
			}
		}
	}

	//for (int i = 0; i < 20; i++) {
	//	for (int j = 0; j < 20; j++) {
	//		cout << canvas[i][j] << " ";
	//	}
	//	cout << endl;
	//}

	for (int i = y; i < y + 5; i++) {
		for (int j = x; j < x + 5; j++) {
			pattern[i-y][j-x] = (char) canvas[i][j];
		}
	}
}
