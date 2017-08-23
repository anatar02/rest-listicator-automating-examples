package uk.co.compendiumdev.restlisticator.payloads;

public class ListPayloadBuilder {
    private ListPayload listPayload;

    public ListPayloadBuilder(){
        listPayload = new ListPayload();
    }

    public ListPayloadBuilder with() {
        return this;
    }

    public ListPayloadBuilder title(String title) {
        listPayload.setTitle(title);
        return this;
    }

    public ListPayloadBuilder description(String description) {
        listPayload.setDescription(description);
        return this;
    }

    public ListPayloadBuilder and() {
        return this;
    }

    public ListPayloadBuilder guid(String guid) {
        listPayload.setGuid(guid);
        return this;
    }

    public ListPayload build() {
        return listPayload;
    }
}
