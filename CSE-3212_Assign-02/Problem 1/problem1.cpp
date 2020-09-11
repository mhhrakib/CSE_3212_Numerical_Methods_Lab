#include<bits/stdc++.h>
using namespace std;

const double g = 9.81;
const double Q = 20.00;
const double lo = 0.5, hi = 2.5;

void getAc_B (double y, double &Ac, double &B)
{
    Ac = 3*y + (y*y)/2;
    B = 3 + y;
}

double getFuncValue (double y)
{
    double Ac, B;
    getAc_B(y, Ac, B);
    return (1 -  (Q*Q*B)/(g*Ac*Ac*Ac));
}

double calcError(double neww, double old)
{
    return abs((neww - old)*100/neww);
}

void printValue ()
{
    double loCopy = lo, f_x;
    int cnt = 0;
    printf("\t    y \t\t\t   f(y) \n");
    while(loCopy <= hi + 0.001) {
        double f_x = getFuncValue(loCopy);
        printf("\t%lf \t|\t %lf\n",loCopy,f_x);
        loCopy += 0.1;
    }
}

void biSection (double ust, double lower, double upper) // ust = user specified tolerance
{
    if(getFuncValue(upper) * getFuncValue(lower) > 0.0) {
        printf("\nThere is either no root or there is even number of roots in the specified range");
        return;
    }

    printf("\nIteration No.\tUpper\t\tLower\t\tx_m\t\tf(x_m)\t\tRel. Approx. Error\n\n");
    int cnt = 0;
    double neww, old = 0.0, mid, hi, lo, midVal, lowVal;
    hi = upper;
    lo = lower;
    while(hi-lo >= ust) {
        mid = (lo + hi)/ 2.0;
        midVal = getFuncValue(mid);
        lowVal = getFuncValue(lo);

        if(midVal * lowVal <0.0)
            hi = mid;
        else
            lo = mid;

        cnt++;
        neww = mid;
        if(cnt == 1)
            printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t---(N/A)---\n", cnt,hi, lo, mid, midVal);
        else
            printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t%.6lf\n", cnt,hi, lo, mid, midVal, calcError(neww, old));
        old = neww;
    }
    printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t%.6lf\n", cnt + 1,hi, lo, mid, midVal, calcError(neww, old));
    printf("\nThe root is = %lf\n", mid);

}

double getFalseRoot(double x_l, double x_u)
{

    double fx_l = getFuncValue(x_l);
    double fx_u = getFuncValue(x_u);
    double x_r = (x_u * fx_l - x_l * fx_u)/(fx_l - fx_u);
    return x_r;
}

void falsePosition (double ust, double x_l, double x_u)
{
    double x_r, fx_l, fx_u, fx_r, x_r_o;

    x_r = getFalseRoot(x_l, x_u);
    fx_r = getFuncValue(x_r);
    fx_l = getFuncValue(x_l);

    if(fx_l * fx_u > 0.0) {
        printf("\nThere is either no root or there is even number of roots in the specified range");
        return;
    }

    double error = 100.00 ;
    int cnt = 1;
    printf("\nIteration No.\tx_u\t\tx_l\t\tx_r\t\tf(x_r)\t\tRel. Approx. Error\n\n");
    while (error > ust) {
        if(cnt == 1)
            printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t---(N/A)---\n", cnt,x_u,x_l,x_r,fx_r);
        else
            printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t%.6lf\n", cnt,x_u,x_l,x_r,fx_r, error);

        if(fx_l * fx_r < 0)
            x_u = x_r;
        else
            x_l = x_r;
        x_r_o = x_r;
        x_r = getFalseRoot(x_l, x_u);
        fx_r = getFuncValue(x_r);
        fx_l  = getFuncValue(x_l);
        error = calcError(x_r, x_r_o);
        cnt++;
    }

    printf("\t%d\t%.6lf \t%.6lf \t%.6lf \t%.6lf \t%.6lf\n", cnt,x_u,x_l,x_r,fx_r, error);
    printf("\nThe root is = %lf\n", x_r);
}

int main()
{
    double hi = 2.5, lo = 0.5, accuracy = 0.001, mid;
    int c;
    double loCopy = lo;

    printValue();

    printf("\n1.Bisection\t2.False Position\nEnter your choice: ");
    scanf("%d",&c);

    printf("\nEnter Low: ");
    scanf("%lf", &lo);
    printf("Enter High: ");
    scanf("%lf", &hi);
    printf("Enter Accuracy: ");
    scanf("%lf", &accuracy);

    if(c == 1)
        biSection(accuracy, lo, hi);
    if(c == 2)
        falsePosition(accuracy, lo, hi);

    return 0;
}
