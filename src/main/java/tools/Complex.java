package tools;

/**
 * Minimal mutable complex number.
 *
 * <p>Provides only the operations required by the simulations: addition,
 * multiplication and the modulus. Arithmetic methods mutate this instance and
 * return it, which allows chaining but makes them unsuitable for shared values.
 */
public class Complex {
    private double a, b;

    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex(double[] ab) {
        this.a = ab[0];
        this.b = ab[1];
    }

    public Complex() {
        this.a = 0;
        this.b = 0;
    }

    /** Multiplies this complex number by {@code x} in place. */
    public Complex mult(Complex x) {
        double real = this.a * x.a - this.b * x.b;
        double imag = this.a * x.b + this.b * x.a;
        this.a = real;
        this.b = imag;
        return this;
    }

    /** Adds {@code x} to this complex number in place. */
    public Complex add(Complex x) {
        this.a += x.a;
        this.b += x.b;
        return this;
    }

    /** @return the modulus (absolute value) of this complex number. */
    public double norm() {
        return Math.sqrt(a*a+b*b);
    }
}
