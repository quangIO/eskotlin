/*
 * Copyright (c) 2016. Michael Buhot m.buhot@gmail.com
 */

package mbuhot.eskotlin.query.fulltext

import mbuhot.eskotlin.query.should_render_as
import org.junit.Test


class CommonTest {
    @Test
    fun `test common terms query`() {
        val query = common {
            "body" {
                query = "this is bonsai cool"
                cutoff_frequency = 0.001f
            }
        }

        query should_render_as """
        {
            "common": {
                "body": {
                    "query": "this is bonsai cool",
                    "high_freq_operator": "OR",
                    "low_freq_operator": "OR",
                    "cutoff_frequency": 0.001,
                    "boost": 1.0
                }
            }
        }
        """
    }

    @Test
    fun `test common terms with low_frequency operator`() {
        val query = common {
            "body" {
                query = "nelly the elephant as a cartoon"
                cutoff_frequency = 0.001f
                low_freq_operator = "and"
            }
        }

        query should_render_as """
            {
                "common": {
                    "body": {
                        "query": "nelly the elephant as a cartoon",
                        "high_freq_operator": "OR",
                        "low_freq_operator": "AND",
                        "cutoff_frequency": 0.001,
                        "boost": 1.0
                    }
                }
            }
            """
    }

    @Test
    fun `test common terms with minimum_should_match`() {
        val query = common {
            "body" {
                query = "nelly the elephant as a cartoon"
                cutoff_frequency = 0.001f
                minimum_should_match.low_freq = 2
            }
        }
        query should_render_as """
            {
                "common": {
                    "body": {
                        "query": "nelly the elephant as a cartoon",
                        "high_freq_operator": "OR",
                        "low_freq_operator": "OR",
                        "cutoff_frequency": 0.001,
                        "minimum_should_match": {
                            "low_freq": "2"
                        },
                        "boost": 1.0
                    }
                }
            }
        """
    }

    @Test
    fun `test common terms with separate minimum_should_match high and low`() {
        val query = common {
            "body" {
                query = "nelly the elephant not as a cartoon"
                cutoff_frequency = 0.001f
                minimum_should_match {
                    low_freq = 2
                    high_freq = 3
                }
            }
        }

        query should_render_as """
            {
                "common": {
                    "body": {
                        "query": "nelly the elephant not as a cartoon",
                        "high_freq_operator": "OR",
                        "low_freq_operator": "OR",
                        "cutoff_frequency": 0.001,
                        "minimum_should_match": {
                            "low_freq": "2",
                            "high_freq": "3"
                        },
                        "boost": 1.0
                    }
                }
            }
            """
    }
}