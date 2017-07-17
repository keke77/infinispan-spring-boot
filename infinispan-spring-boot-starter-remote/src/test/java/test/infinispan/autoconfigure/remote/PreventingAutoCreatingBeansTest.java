package test.infinispan.autoconfigure.remote;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        InfinispanRemoteAutoConfiguration.class,
        InfinispanRemoteCacheManagerAutoConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off",
        "infinispan.remote.client-properties=test-hotrod-client.properties",
        "infinispan.remote.enabled=false"
    }
)
public class PreventingAutoCreatingBeansTest {

    @Autowired
    private ListableBeanFactory beanFactory;

    @Test
    public void testIfNoDefaultClientWasCreated() {
        assertThat(beanFactory.getBeansOfType(RemoteCacheManager.class)).isEmpty();
    }

    @Test
    public void testIfNoEmbeddedCacheManagerWasCreated() {
        assertThat(beanFactory.containsBeanDefinition("defaultCacheManager")).isFalse();
    }
}