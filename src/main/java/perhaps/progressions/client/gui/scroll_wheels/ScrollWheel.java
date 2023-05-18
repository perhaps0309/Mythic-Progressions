package perhaps.progressions.client.gui.scroll_wheels;

import java.util.ArrayList;
import java.util.List;

public class ScrollWheel {
    private final ScrollWheel parent;
    private final List<WheelOption> options = new ArrayList<>();

    public ScrollWheel(ScrollWheel parent) {
        this.parent = parent;
    }

    public ScrollWheel getParent() {
        return parent;
    }

    public void addOption(WheelOption option) {
        options.add(option);
    }

    public List<WheelOption> getOptions() {
        return options;
    }
    public void setOptions(List<WheelOption> options) {
        this.options.clear();
        this.options.addAll(options);
    }
}