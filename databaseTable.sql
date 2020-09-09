CREATE TABLE `fast_insert` (
  `match_id` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `market_id` int(11) NOT NULL,
  `outcome_id` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `specifiers` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `date_insert` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
COMMIT;