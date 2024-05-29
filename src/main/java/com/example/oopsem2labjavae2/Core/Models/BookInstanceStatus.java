package com.example.oopsem2labjavae2.Core.Models;

import lombok.Getter;

@Getter
public enum BookInstanceStatus
{
    Available(0),
    BorrowedHome(1),
    BorrowedInLibrary(2);

    private final int value;

    BookInstanceStatus(int value)
    {
        this.value = value;
    }

    public static BookInstanceStatus parse(int value)
    {
        switch (value)
        {
            case 0: return BookInstanceStatus.Available;
            case 1: return BookInstanceStatus.BorrowedHome;
            case 2: return BookInstanceStatus.BorrowedInLibrary;
            default: throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    @Override
    public String toString() {
        switch (value)
        {
            case 0: return "BookInstanceStatus.Available";
            case 1: return "BookInstanceStatus.BorrowedHome";
            case 2: return "BookInstanceStatus.BorrowedInLibrary";
            default: throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}
