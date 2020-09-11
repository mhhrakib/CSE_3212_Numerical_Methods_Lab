import math


def get_fx(x):
    return x**100


def get_f_prime_x(x):
    return 100*x**99


def get_val(x_i):
    return 0.99*x_i


def get_fx_b(x):
    if(x<0):
        a = -x;
        return -1*a**(1/3)
    return (x**(1/3))


def get_f_prime_x_b(x):
    b = get_fx_b(x)
    b *= b
    return 1/b


def get_val_b(x_i):
    return -2.0*x_i


def calc_error(new,old):
    return abs((new-old)*100/new)


def newton_raphson():
    print("Problem (a):")
    print("Intial guess X0 = 0.1, next five estimates are:\n")
    a = 0.1
    b = 0
    print("Iteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n")
    for i in range(1,6):
        b = get_val(a)
        print(i, b, get_fx(b), get_f_prime_x(b), calc_error(b, a), sep = "        ")
        a = b

    print("Problem (b):\n")
    print("Intial guess X0 = 0.1, next five estimates are:\n")
    a = 0.1
    print("Iteration No.\tX_i\t\tF(X_i)\t\tF'(X_i)\t\tRel. Approx. Error\n");
    for i in range(1,11):
        b = get_val_b(a);
        print(i, b, 3*get_fx_b(b), get_f_prime_x_b(b), calc_error(b, a), sep = "\t\t")
        a = b;


newton_raphson()
