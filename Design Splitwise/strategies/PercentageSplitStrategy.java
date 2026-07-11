package strategies;

import java.util.List;

import entities.PercentageSplit;
import entities.Split;
import exceptions.InvalidSplitException;

public class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public void validate(List<Split> splits, double totalAmount) {
        double totalPercentage = 0.0; 
        for(Split split : splits) {
            if(!(split instanceof PercentageSplit)) {
                throw new InvalidSplitException("All splits must be PercentageSplits for percentage split type");
            }

            totalPercentage += ((PercentageSplit) split).getPercentage();
        }

        if(Math.abs(totalPercentage - 100.0) > 0.01) {
            throw new InvalidSplitException(
                String.format("Percentage (%.2f%%) dont sum to 100%%", totalPercentage)
            );
        }
    }

    @Override
    public void calculateSplits(List<Split> splits, double totalAmount) {
        double allocatedAmount = 0;
        for(int i = 0; i < splits.size() - 1; i++) {
            PercentageSplit ps = (PercentageSplit) splits.get(i);
            double amount = Math.round(totalAmount * ps.getPercentage() / 100.0 * 100.0) / 100.0;
            ps.setAmount(amount);
            allocatedAmount += amount;
        }

        //Last person gets the remainder to handle rounding
        double remainder = Math.round((totalAmount - allocatedAmount) * 100.0) / 100.0;
        splits.get(splits.size() - 1).setAmount(remainder);
    }
}
