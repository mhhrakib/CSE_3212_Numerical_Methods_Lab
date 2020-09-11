import math


def get_fx(x):
    return x**100


def get_f_prime_x(x):
    return 100*x**99


def get_val(x_i):
    return 0.99*x_i


def get_fx_b(x):
    return 3*(x**0.33333333333)


def get_f_prime_x_b(x):
    return 1/(x**(2/3))


def get_val_b(x_i):
    return -2.0*x_i


def calc_error(new,old):
    return abs((new-old)*100/new)


def newton_raphson():
    print("Problem (a): \n")
    print("Intial guess X0 = 0.1, next five estimates are:\n")
    a = 0.1
    b = 0
    print("\nIteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n\n")
    for i in range(1,6):
        b = get_val(a)
        print("\t%\t%.11lf \t%.11lf \t%.11lf \t%.11lf\n", i, b, get_fx(b), get_f_prime_x(b), calc_error(b, a))
        
        a = b

    print("Problem (b):\n")
    print("Intial guess X0 = 0.1, next five estimates are:\n")
    a = 0.1
    print("\nIteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n\n");
    for i in range(1,6):
        b = get_val_b(a);
        print("\t%d\t%.11lf \t%.11lf \t%.11lf \t%.11lf\n", i, b, get_fx_b(b), get_f_prime_x_b(b), calc_error(b, a))
        a = b;


newton_raphson()

