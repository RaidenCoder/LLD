package observer;

import entities.Expense;
import entities.Split;

public class EmailNotificationObserver implements ExpenseObserver {
    @Override
    public void onExpenseAdded(Expense expense) {
        for(Split split: expense.getSplits()) {
            String detail = split.getUserId().equals(expense.getPaidByUserId())
                ? String.format("you paid $%.2f", expense.getAmount())
                : String.format("your share is $%.2f", split.getAmount());
            System.out.printf("[Email to %s] New expense '%s': %s%n", 
                    split.getUserId(), expense.getDescription(), detail);
        }
    }

    @Override
    public void onSettlement(String fromUserId, String toUserId, double amount) {
        //Notify both parties on the settlement
        System.out.printf("[Email to %s] You paid %s $%.2f%n", fromUserId, toUserId, amount);
        System.out.printf("[Email to %s] %s paid you $%.2f", toUserId, fromUserId, amount);
    }
}
