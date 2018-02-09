package com.bicloo.bicloolocation.events;


import com.bicloo.bicloolocation.modele.Station;

import java.util.List;

public class StationsResponseEvent extends BaseEvent {

    private List<Station> stations;

    public StationsResponseEvent(List<Station> stations) {
        super();
        this.stations = stations;
    }

    public StationsResponseEvent(Throwable t) {
        super(t);
    }

    public List<Station> getStations() {
        return stations;
    }
}
