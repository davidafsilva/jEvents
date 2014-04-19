/*
 * Copyright (c) 2014, David Silva
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *         * Redistributions of source code must retain the above copyright
 *             notice, this list of conditions and the following disclaimer.
 *         * Redistributions in binary form must reproduce the above copyright
 *             notice, this list of conditions and the following disclaimer in the
 *             documentation and/or other materials provided with the distribution.
 *         * Neither the name of the <organization> nor the
 *             names of its contributors may be used to endorse or promote products
 *             derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.junit.Test;
import pt.davidafsilva.jevents.Event;
import pt.davidafsilva.jevents.EventListener;
import pt.davidafsilva.jevents.dispatcher.EventDispatcher;
import pt.davidafsilva.jevents.registry.EventRegistry;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * A simple test
 *
 * @author David Silva
 */
public class SimpleTest {

	@Test
	public void sync_event_test() {
		// dispatch sync
		EventDispatcher.INSTANCE.dispatch(createSimpleEventScenario());

		// check calls
		validate();
	}

	@Test
	public void async_event_test() throws InterruptedException {
		// dispatch async
		EventDispatcher.INSTANCE.dispatchAsync(createSimpleEventScenario());

		// force sleep
		Thread.sleep(500);

		// check calls
		validate();
	}

	private SimpleEvent createSimpleEventScenario() {
		// unregisters previously registered events
		EventRegistry.INSTANCE.unregisterAll(SimpleEvent.class);

		// check get by event class
		assertThat(EventRegistry.INSTANCE.getEventListeners(SimpleEvent.class).size(), is(0));

		// register listeners
		EventRegistry.INSTANCE.register(new SimpleEventListener());
		EventRegistry.INSTANCE.register(new SimpleEventListener());

		// check get by event class
		assertThat(EventRegistry.INSTANCE.getEventListeners(SimpleEvent.class).size(), is(2));

		// create and event
		SimpleEvent event = new SimpleEvent();

		// check get by event object
		assertThat(EventRegistry.INSTANCE.getEventListeners(event).size(), is(2));

		return event;
	}

	private void validate() {
		// check calls
		Collection<SimpleEventListener> eventListeners =
				EventRegistry.INSTANCE.getEventListeners(SimpleEvent.class);
		for (SimpleEventListener listener : eventListeners) {
			assertThat(listener.getCalls(), is(1));
		}
	}

	private static class SimpleEventListener implements EventListener<SimpleEvent> {

		private int calls;

		@Override
		public void handle(SimpleEvent event) {
			calls++;
		}

		public int getCalls() {
			return calls;
		}
	}

	private static final class SimpleEvent implements Event {
	}
}
