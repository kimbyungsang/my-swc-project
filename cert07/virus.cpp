#include <iostream>
using namespace std;
#define MAX_NODE  10007

struct Node {
	int id;
	int fileSize;
	int backup;
	bool isFile;
	int fileCnt;

	Node* parent;
	Node* next = NULL;;
	Node* prev = NULL;
	Node* child = NULL;

	Node* hNext = NULL;

};

struct Hash {
	Node* node;
	Hash* next;
};

Node _node[MAX_NODE * 3];
int nodeCnt = 0;
Node* root;

Node* myHash[MAX_NODE];
Node* node;
int avg;

Node* newNode(int id, int fileSize, int backup) {
	_node[nodeCnt].id = id;
	_node[nodeCnt].fileSize = fileSize;
	_node[nodeCnt].backup = backup;
	_node[nodeCnt].fileCnt = 0;
	_node[nodeCnt].isFile = false;


	return &_node[nodeCnt++];
}

int getHash(int id) {
	return id % MAX_NODE;
}


void init() {
//	cout << "init" << endl;
	nodeCnt = 0;
	root = newNode(1000, 0, 0);
	root->parent = NULL;
	root->child = newNode(-1, 0, 0);
	root->child->prev = root->child;
	root->child->next = root->child;

	myHash[getHash(root->id)] = root;



}

Node* getNode(int id) {

	for (Node* iter = myHash[getHash(id)]; iter != NULL; iter = iter->hNext) {
		if (iter->id == id) {
			return iter;
		}
	}

	return NULL;
}

void printDFS(Node* node) {
	if (node->parent == NULL) {
		//	cout << "null" << " " << node->id << " " << node->fileSize << " " << node->backup << " " << node->fileCnt << " " << node->isFile << endl;
		printf("null\t%d\t%d\t%d\t%d\t%d\n", node->id, node->fileSize, node->backup, node->fileCnt, node->isFile);
	}
	else {
		//	cout << node->parent->id << " " << node->id << " " << node->fileSize << " " << node->backup << " " << node->fileCnt << " " << node->isFile << endl;
		printf("%d\t%d\t%d\t%d\t%d\t%d\n", node->parent->id, node->id, node->fileSize, node->backup, node->fileCnt, node->isFile);

	}

	for (Node* iter = node->child->next; iter != node->child; iter = iter->next) {
		printDFS(iter);
	}

}


void printBFS(Node* node) {
	int front = 0;
	int rear = 0;
	Node* queue[10000];

	if (node->parent == NULL) {
		printf("null\t%d\t%d\t%d\t%d\t%d\n", node->id, node->fileSize, node->backup, node->fileCnt, node->isFile);
	}
	else {
		printf("%d\t%d\t%d\t%d\t%d\t%d\n", node->parent->id, node->id, node->fileSize, node->backup, node->fileCnt, node->isFile);
	}

	queue[front++] = node;

	while (front != rear) {
		Node* tmp = queue[rear++];
		for (Node* iter = tmp->child->next; iter != tmp->child; iter = iter->next) {
			if (iter->parent == NULL) {
				printf("null\t%d\t%d\t%d\t%d\t%d\n", iter->id, iter->fileSize, iter->backup, iter->fileCnt, iter->isFile);
			}
			else {
				printf("%d\t%d\t%d\t%d\t%d\t%d\n", iter->parent->id, iter->id, iter->fileSize, iter->backup, iter->fileCnt, iter->isFile);
			}
			queue[front++] = iter;
		}
	}
}

void printMyHash() {
	for (int i = 0; i < MAX_NODE; i++) {
		if (myHash[i] != NULL) {
			for (Node* iter = myHash[i]; iter != NULL; iter = iter->hNext) {
				if (iter->parent == NULL) {
					printf("%d\tnull\t%d\t%d\t%d\t%d\t%d\n", i, iter->id, iter->fileSize, iter->backup, iter->fileCnt, iter->isFile);
				}
				else {
					printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\n", i, iter->parent->id, iter->id, iter->fileSize, iter->backup, iter->fileCnt, iter->isFile);
				}
			}
		}
	}
}

int add(int id, int pid, int fileSize) {
//	cout << "add :" << pid << " " << id << " " << fileSize << endl;

	//create node
	node = newNode(id, fileSize, fileSize);
	node->child = newNode(-1, 0, 0);
	node->child->next = node->child;
	node->child->prev = node->child;

	// add parent
	node->parent = getNode(pid);

	// add node to parent's child
	node->next = node->parent->child;
	node->prev = node->parent->child->prev;
	node->next->prev = node;
	node->prev->next = node;

	// add metadata
	if (fileSize > 0) {
		node->isFile = true;
		node->fileCnt = 1;

		for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
			iter->fileSize += fileSize;
			iter->backup += fileSize;
			iter->fileCnt += 1;
		}
	}

	// add node to hash
	node->hNext = myHash[getHash(node->id)];
	myHash[getHash(node->id)] = node;


	// printDFS(root);
	// printBFS(root);
	// printMyHash();
	return node->parent->fileSize;
}

int move(int id, int pid) {
//	cout << "move:" << id << " " << pid << endl;

	// remove from the parent's child
	node = getNode(id);
	node->next->prev = node->prev;
	node->prev->next = node->next;

	// remove metadata
	for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
		iter->fileSize -= node->fileSize;
		iter->backup -= node->backup;
		iter->fileCnt -= node->fileCnt;
	}

	// add to new parent's child
	Node* pnt = getNode(pid);

	node->parent = pnt;
	node->next = pnt->child;
	node->prev = pnt->child->prev;
	node->next->prev = node;
	node->prev->next = node;

	// add metadat
	for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
		iter->fileSize += node->fileSize;
		iter->backup += node->backup;
		iter->fileCnt += node->fileCnt;
	}

//	printDFS(root);

	return node->parent->fileSize;
}


void infectDFS(Node* node) {
	for (Node* iter = node->child->next; iter != node->child; iter = iter->next) {
		infectDFS(iter);
	}
	node->fileSize += (avg * node->fileCnt);
}

int infect(int id) {

	avg = 0;
	if (root->fileCnt != 0)
		avg = root->fileSize / root->fileCnt;

//	cout << "infe:" << id << " " << avg << endl;

	node = getNode(id);
	infectDFS(node);

	for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
		iter->fileSize += (node->fileCnt * avg);
	}

//	printDFS(root);


	return node->fileSize;
}

void recoverDFS(Node* node) {
	for (Node* iter = node->child->next; iter != node->child; iter = iter->next) {
		recoverDFS(iter);
	}

	node->fileSize = node->backup;
}

int recover(int id) {
//	cout << "reco:" << id << " " << endl;

	Node* node = getNode(id);


	for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
		iter->fileSize -= (node->fileSize - node->backup);
	}

	recoverDFS(node);

//	printDFS(root);
	return node->fileSize;
}

int remove(int id) {
//	cout << "remo:" << id << " " << endl;

	node = getNode(id);

	for (Node* iter = node->parent; iter != NULL; iter = iter->parent) {
		iter->fileSize -= node->fileSize;
		iter->fileCnt -= node->fileCnt;
		iter->backup -= node->backup;
	}

	node->next->prev = node->prev;
	node->prev->next = node->next;


//	printDFS(root);

	return node->fileSize;
}

// do not touch
void do_test(int tc) {

	int n;
	scanf("%d", &n);
	int cmd;
	int arg[3];
	init();
	while (n--) {
		scanf("%d", &cmd);
		if (cmd == 1) {
			scanf("%d %d %d", arg + 0, arg + 1, arg + 2);
			printf("%d\n", add(arg[0], arg[1], arg[2]));
		}
		else if (cmd == 2) {
			scanf("%d %d", arg + 0, arg + 1);
			printf("%d\n", move(arg[0], arg[1]));
		}
		else if (cmd == 3) {
			scanf("%d", arg + 0);
			printf("%d\n", infect(arg[0]));
		}
		else if (cmd == 4) {
			scanf("%d", arg + 0);
			printf("%d\n", recover(arg[0]));
		}
		else if (cmd == 5) {
			scanf("%d", arg + 0);
			printf("%d\n", remove(arg[0]));
		}
	}
}

int main() {
	int tc;
	freopen("input.txt", "r", stdin);
//	freopen("output.txt", "w", stdout);

	scanf("%d", &tc);
	for (int i = 0; i < tc; i++) {
		do_test(i);
	}

	return 0;
}
