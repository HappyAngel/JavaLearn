package happyangel.learnjava.ZookeeperLearn;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher;

/**
 * Created by xionglei on 16-8-4.
 */
public class Master implements Watcher {
    ZooKeeper zk;
    String hostPort;
    Master(String hostPort) {
        this.hostPort = hostPort;
    }
}
