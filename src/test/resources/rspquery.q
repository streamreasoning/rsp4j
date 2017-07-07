PREFIX : <http://streamreasoning.org/iminds/massif/>

SELECT *
FROM NAMED WINDOW  :win1 [RANGE 8 s, SLIDE 2s] ON STREAM :stream1
WHERE  {

    WINDOW ?w {
        ?s ?p ?o
    }

}
