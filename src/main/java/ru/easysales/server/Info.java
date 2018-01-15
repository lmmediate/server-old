package ru.easysales.server;

public class Info {

    private static final int itemsPerPage = 30;
    private int itemCount;
    private int numPages;

    public Info(ItemRepository repository) {
        itemCount = (int) repository.count();
        numPages = (int) Math.ceil((double) itemCount / itemsPerPage);
    }

    public static int getItemsPerPage() {
        return itemsPerPage;
    }

    public long getItemCount() {
        return itemCount;
    }

    public int getNumPages() {
        return numPages;
    }
}
