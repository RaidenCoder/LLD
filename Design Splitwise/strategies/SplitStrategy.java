package strategies;

import java.util.List;

import entities.Split;

public interface SplitStrategy {
    void validate(List<Split> splits, double totalAmount);
    void calculateSplits(List<Split> splits, double totalAmount);
}
