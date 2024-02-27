package me.lofro.utils.data;

import lombok.Getter;

import java.util.Objects;

public class Pair<A, B> {

    private @Getter A first;
    private @Getter B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public void set(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Pair<?, ?> other) {
            return Objects.equals(first, other.first) && Objects.equals(second, other.second);
        } else {
            return false;
        }
    }
}
