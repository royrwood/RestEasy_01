/**
 * This class is generated by jOOQ
 */
package org.roy.generated;


import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.roy.generated.tables.People;
import org.roy.generated.tables.records.PeopleRecord;


/**
 * A class modelling foreign key relationships between tables of the <code>public</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<PeopleRecord, Integer> IDENTITY_PEOPLE = Identities0.IDENTITY_PEOPLE;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<PeopleRecord> PEOPLE_PKEY = UniqueKeys0.PEOPLE_PKEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<PeopleRecord, Integer> IDENTITY_PEOPLE = createIdentity(People.PEOPLE, People.PEOPLE.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<PeopleRecord> PEOPLE_PKEY = createUniqueKey(People.PEOPLE, People.PEOPLE.ID);
	}
}
