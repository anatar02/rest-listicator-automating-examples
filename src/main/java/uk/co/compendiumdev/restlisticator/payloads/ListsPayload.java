package uk.co.compendiumdev.restlisticator.payloads;

import java.util.List;

/**
 * Created by Alan on 23/08/2017.
 */
public class ListsPayload {
    private List<ListPayload> lists;

    public List<ListPayload> getLists() {
        return lists;
    }

    public void setLists(List<ListPayload> lists) {
        this.lists = lists;
    }
}
