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

package pt.davidafsilva.jevents.registry;

import pt.davidafsilva.jevents.Event;
import pt.davidafsilva.jevents.EventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This entity is responsible for the management of event listeners.
 *
 * This event registry is based on final class implementations.
 * Inheritance is not supported.
 *
 * @author David Silva
 * @since 1.0
 */
public enum EventRegistry {
	INSTANCE;

	// map for the event class -> listeners
	private ConcurrentMap<Class<?>, Collection<? extends EventListener<?>>> listeners = new ConcurrentHashMap<>();

	/**
	 * Registers a given event listener which shall be triggered whenever
	 * a event with the concrete event type associated with the listener is dispatched.
	 *
	 * @param listener
	 * 		the event listener
	 * @param <E>
	 * 		the Event type associated with the listener
	 * @param <L>
	 * 		the listener type
	 * @return <code>true</code> if the listener is successfully added, <code>false</code> otherwise.
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>.
	 */
	public <E extends Event, L extends EventListener<E>> boolean register(final L listener) {
		return initEventCollection(listener.getEventClass()).add(listener);
	}

	/**
	 * Removes the given event listener, if it's registered.
	 *
	 * @param listener
	 * 		the event listener
	 * @param <E>
	 * 		the Event type associated with the listener
	 * @param <L>
	 * 		the listener type
	 * @return <code>true</code> if the listener is successfully removed, <code>false</code> otherwise.
	 */
	public <E extends Event, L extends EventListener<E>> boolean unregister(final L listener) {
		return initEventCollection(listener.getEventClass()).remove(listener);
	}

	/**
	 * Removes all of the listeners associated with the given event type.
	 *
	 * @param clazz
	 * 		the event class
	 * @param <E>
	 * 		the event type
	 */
	public <E extends Event> void unregisterAll(final Class<E> clazz) {
		initEventCollection(clazz).clear();
	}

	/**
	 * Returns all the registered listeners for the given event type.
	 *
	 * @param clazz
	 * 		the event type class
	 * @param <E>
	 * 		the event type
	 * @param <L>
	 * 		the listener type
	 * @return a collection with all of the listeners
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>.
	 */
	public <E extends Event, L extends EventListener<E>> Collection<L> getEventListeners(final Class<E> clazz) {
		if (clazz == null) {
			throw new NullPointerException("Invalid event class.");
		}
		return Collections.unmodifiableCollection(initEventCollection(clazz));
	}

	/**
	 * Returns all the registered listeners for the given event type.
	 *
	 * @param event
	 * 		the actual event object
	 * @param <E>
	 * 		the event type
	 * @param <L>
	 * 		the listener type
	 * @return a collection with all of the listeners
	 * @throws java.lang.NullPointerException
	 * 		if <code>event</code> is <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Event, L extends EventListener<E>> Collection<L> getEventListeners(final E event) {
		Class<E> eClass = (Class<E>) event.getClass();
		return getEventListeners(eClass);
	}


	/**
	 * Internal procedure for initializing the collection of listeners associated with a given
	 * event class at the listeners map, if necessary.
	 *
	 * @param eventClass
	 * 		the event type
	 * @param <E>
	 * 		the event type
	 * @param <L>
	 * 		the listener type
	 * @return the newly created collection of listeners or the existent one.
	 */
	@SuppressWarnings("unchecked")
	private <E extends Event, L extends EventListener<E>> Collection<L> initEventCollection(Class<E> eventClass) {
		Collection<L> actualListeners = (Collection<L>) listeners.get(eventClass);
		if (actualListeners == null) {
			final Collection<L> list = new ArrayList<>();
			actualListeners = (Collection<L>) listeners.putIfAbsent(eventClass, list);
			if (actualListeners == null) {
				actualListeners = list;
			}
		}

		return actualListeners;
	}
}
