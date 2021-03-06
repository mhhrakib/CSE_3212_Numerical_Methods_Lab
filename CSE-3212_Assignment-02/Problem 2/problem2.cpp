#include<bits/stdc++.h>
using namespace std;

double getFx(double x)
{
    double res = pow(x,100);
    //printf("%lf^100 = %lf",x, res);
    return res;
}

double getFprimeX(double x)
{
    return 100*pow(x,99);
}

double getVal(double x_i)
{
    //double res = x_i - (getFx(x_i)/getFprimeX(x_i));
    //return res;
    return 0.99*x_i;
}

double getFxB(double x)
{
    return 3*pow(x,0.33333333333);
}

double getFprimeXB(double x)
{
    return 1/pow(x,2/3);
}

double getValB(double x_i)
{
    //double res = x_i - (getFxB(x_i)/getFprimeXB(x_i));
    //return res;
    return -2.0*x_i;

}

double calcError(double neww, double old)
{
    return abs((neww - old)*100/neww);
}


void newton_raphson()
{
    printf("Problem (a):\n");
    printf("Intial guess X0 = 0.1, next five estimates are:\n");

    double a = 0.1, b;
    printf("\nIteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n\n");
    for(int i = 1; i<=5; i++) {
        b = getVal(a);
        printf("\t%d\t%.11lf \t%.11lf \t%.11lf \t%.11lf\n", i,b,getFx(b),getFprimeX(b),calcError(b,a));
        a = b;
    }


    printf("Problem (b):\n");
    printf("Intial guess X0 = 0.1, next five estimates are:\n");
    a = 0.1;
    printf("\nIteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n\n");
    for(int i = 1; i<=5; i++) {
        b = getValB(a);
        printf("\t%d\t%.11lf \t%.11lf \t%.11lf \t%.11lf\n", i,b,getFxB(b),getFprimeXB(b),calcError(b,a));
        a = b;
    }



}

int main()
{
    printf("\n%lf\n",pow(-0.2,0.3333333));
    newton_raphson();

}
