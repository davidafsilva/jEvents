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

package pt.davidafsilva.jevents.dispatcher;

import pt.davidafsilva.jevents.Event;
import pt.davidafsilva.jevents.EventListener;
import pt.davidafsilva.jevents.registry.EventRegistry;

import java.util.Collection;

/**
 * The event dispatcher, which is responsible for the firing of
 * all the events.
 *
 * All the listeners previously registered in {@link pt.davidafsilva.jevents.registry.EventRegistry}
 * are triggered sequentially, respecting the ordering that they were registered.
 *
 * @author David Silva
 * @since 1.0
 */
public enum EventDispatcher {
	INSTANCE;

	/**
	 * Dispatches the given event synchronously
	 *
	 * @param event
	 * 		the event being dispatched
	 * @param <E>
	 * 		the event type
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>
	 */
	public <E extends Event> void dispatch(final E event) {
		internalDispatch(event, false);
	}

	/**
	 * Dispatches the given event asynchronously
	 *
	 * @param event
	 * 		the event being dispatched
	 * @param <E>
	 * 		the event type
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>
	 */
	public <E extends Event> void dispatchAsync(final E event) {
		internalDispatch(event, true);
	}

	/**
	 * Internal procedure for dispatching events
	 *
	 * @param event
	 * 		the event to be dispatched
	 * @param async
	 * 		the async flag
	 * @param <E>
	 * 		the event type
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>
	 */
	private <E extends Event> void internalDispatch(final E event, final boolean async) {
		Collection<EventListener<E>> listeners = EventRegistry.INSTANCE.getEventListeners(event);
		if (!listeners.isEmpty()) {
			DispatcherFactory.INSTANCE.<E>create(async).dispatchTo(event, listeners);
		}
	}

}
