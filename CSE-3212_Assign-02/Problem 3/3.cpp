#include<bits/stdc++.h>
using namespace std;

double getFuncValue(double xn)
{
    double temp = exp(0.5 * xn) - 5 + 5*xn;
    return temp;
}

double getFuncValue2( double x)
{
    double res = sin(x) + cos (1 + x*x) - 1;
    return res;
}

double sec(double xn, double x1)
{
    double temp = ((xn - x1) * getFuncValue(xn))/ (getFuncValue(xn) -  getFuncValue(x1));
    return xn - temp;
}

double sec2(double xn, double x1)
{
    double temp = ((xn - x1) * getFuncValue2(xn))/ (getFuncValue2(xn) -  getFuncValue2(x1));
    return xn - temp;
}

double calcError(double neww, double old)
{
    return abs((neww - old)*100/neww);
}

void secantA(double a, double b, double ust)  // a = xn, b = xn+1
{
    double c, error;
    c = sec(a, b);
    error = calcError(c,b);
    int iter = 1;
    printf("\nIteration No.\tUpper\t\tLower\t\tx_m\t\tf(x_m)\t\tRel. Approx. Error\n\n");
    while (error > ust) {
        printf("\t%d\t%.8lf \t%.8lf \t%.8lf \t%.8lf \t%.8lf\n", iter, c,a,b,getFuncValue(b),error);
        a = b;
        b = c;
        c = sec(a, b);
        error = calcError(c,b);
        iter++;
    }

    printf("\t%d\t%.8lf \t%.8lf \t%.8lf \t%.8lf \t%.8lf\n", iter, c,a,b,getFuncValue(b),error);

    printf("\nThe root is %.8f %d\n", b);
}


void secantB(double a, double b)  // a = xn, b = xn+1
{

    double c, error;
    c = sec2(a, b);
    error = calcError(c,b);
    int iter = 1;
    printf("\nIteration No.\tUpper\t\tLower\t\tx_m\t\tf(x_m)\t\tRel. Approx. Error\n\n");
    while (iter <= 3) {
        printf("\t%d\t%.8lf \t%.8lf \t%.8lf \t%.8lf \t%.8lf\n", iter, c,a,b,getFuncValue2(b),error);
        a = b;
        b = c;
        c = sec2(a, b);
        error = calcError(c,b);
        iter++;
    }

    printf("\t%d\t%.8lf \t%.8lf \t%.8lf \t%.8lf \t%.8lf\n", iter, c,a,b,getFuncValue2(b),error);

    printf("\nThe root is %.8f\n", b);
}


int main()
{
    double hi = 2.0, lo = 0.0, accuracy = 0.0001;
    printf("Problem (3A): \n");
    printf("\nEnter Low: ");
    scanf("%lf", &lo);
    printf("Enter High: ");
    scanf("%lf", &hi);
    printf("Enter Accuracy: ");
    scanf("%lf", &accuracy);
    secantA(lo, hi, accuracy);

    printf("Problem (3B): \n");

    printf("Intial Guesses: (a)\nLow = 1.0\tHigh = 3.0\n");
    secantB(1.0, 3.0);
    puts("");

    printf("Intial Guesses:(b)\nLow = 1.5\tHigh = 2.5\n");
    secantB(1.5,2.5);
    puts("");

    printf("Intial Guesses:(c)\nLow = 1.0\tHigh = 3.0\n");
    secantB(1.5, 2.25);
    return 0;

}
