import sys
import bisect

if len(sys.argv) < 3:
    print 'Usage:<scoremap> <input> <output>'
    exit(-1)
upbd = []
for l in open( sys.argv[1] ):
    upbd.append( float(l.split()[1]) )

fo = open( sys.argv[3], 'w' )
fo.write( '%d\n' % len(upbd) )
for l in open( sys.argv[2] ):
    sc = float( l.split()[2] )
    i = bisect.bisect_left( upbd, sc )
    assert i < len(upbd)
    fo.write( '1 %d:1\n' % i )
fo.close()
