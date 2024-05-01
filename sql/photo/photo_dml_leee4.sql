SELECT *  
FROM (
	  SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT *
			 FROM users
             ORDER BY userid DESC
           ) m  
      )  
WHERE page = 1;