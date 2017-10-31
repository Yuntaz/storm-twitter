package fr.pvillard.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import twitter4j.Status;

/**
 * Receives tweets and emits its words over a certain length.
 */
@SuppressWarnings({"rawtypes", "serial"})
public class TweetSplitterBolt extends BaseRichBolt {

    /** collector */
    private OutputCollector collector;
    /** words for filtering */
    private String[] filters;

    /**
     * constructor
     * 
     * @param filters
     *            words to use for filtering
     */
    public TweetSplitterBolt(String[] filters) {
        this.filters = filters;
    }

    /**
     * {@inheritDoc}
     * 
     * @see backtype.storm.task.IBolt#prepare(java.util.Map,
     *      backtype.storm.task.TopologyContext,
     *      backtype.storm.task.OutputCollector)
     */
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
    }

    /**
     * {@inheritDoc}
     * 
     * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
     */
    @Override
    public void execute(Tuple input) {
        Status tweet = (Status) input.getValueByField("tweet"); //$NON-NLS-1$
        String text = tweet.getText();
        for (String filter : this.filters) {
            if (text.toLowerCase().contains(filter.toLowerCase())) {
                this.collector.emit(new Values(filter, tweet));
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("filter", "tweet")); //$NON-NLS-1$ //$NON-NLS-2$
    }
}