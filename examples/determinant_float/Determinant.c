extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/float2string" string f2s(float);
extern "MinicLib/int2string" string i2s(int);



float sarrus(float[][] matrix, int size) {
    int k, i, j, l, m;
    float det, pos, neg;
    det = 0.0;
    i = 0;
    while( i < (size )){
    	pos = 1 + 0;
    	j = 0;
        while( (j < (size )) ){
            k = (i+j) % size;
            pos = pos * matrix[j][k];
            j = j+1;
        }

        det = det + pos;
        i = i+1;
    }

    l = 0;
    while( l < (size)){
        neg = 1;
        m = 0;
        while( m < (size )){
            int j;
            j = size - 1 - m;
            k = (l + (size ) -j) % size;
            neg = neg * matrix[j][k];
            m = m+1;
        }
            
        det = det - neg;
        l = l+1;
    }
    return det;
}

float bidim(float[][] matrix){
    float det;

    det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    return det;
}


void print_matrix(float[][] matrix, int size){
	print("Data la matrice (");
	print(i2s(size));
	print("*");
	print(i2s(size));
	print_string("):");
	  int i,j;
    i = 0;
    while( i < (size)){
    	 	j = 0;
    	 while( j < (size )){
    		 print("   ");
            print(f2s(matrix[i][j]));
            j = j+1;
        }
        print_string("");
        i = i+1;
    }
}

void main(){
    float det;
    float[][] matrix;
    int SIZE1;
    int SIZE2;

    SIZE1 = 2;
    SIZE2 = 3;
    matrix = new float[SIZE1][SIZE1];

    matrix[0][0] = 0.0;
    matrix[1][0] = 3.3;
    matrix[0][1] = 1.1;
    matrix[1][1] = -1.1;
    print_matrix(matrix, SIZE1);
    det = bidim(matrix);
    print("Il suo determinante e':  ");
    print_string(f2s(det));

    matrix = new float[SIZE2][SIZE2];

    matrix[0][0] = -2.3;
    matrix[0][1] = 1.7;
    matrix[0][2] = 5.5;
    matrix[1][0] = 3.4;
    matrix[1][1] = -1.7;
    matrix[1][2] = -1.9;
    matrix[2][0] = 0.4;
    matrix[2][1] = 1.3;
    matrix[2][2] = -1.6;

    print_matrix(matrix, SIZE2);

    if (SIZE2 == 3){
        det = sarrus(matrix, SIZE2);
        print("Il suo determinante e':  ");
            print_string(f2s(det));
    }
    else{
    	print("Non posso usare Sarrus per una matrice ");
    	print(i2s(SIZE2));
    	print("*");
    	print(i2s(SIZE2));
    	print_string("!!!");
    }
}