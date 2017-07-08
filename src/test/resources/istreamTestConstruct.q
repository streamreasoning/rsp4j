PREFIX : <http://streamreasoning.org/iminds/massif/>

SELECT *
FROM NAMED WINDOW  :win1 [RANGE 5s, SLIDE 5s] ON STREAM :stream1
WHERE  {

    WINDOW ?w {
        ?s ?p ?o
    }

}
